package pro.mikey.justhammers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.util.Objects;

public enum Config {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    public ConfigData config = new ConfigData(false, false);

    public void load() {
        var configPath = HammersPlatform.getConfigDirectory().resolve("justhammers.json");
        if (Files.notExists(configPath)) {
            try {
                // Write default config
                var jsonData = new GsonBuilder().setPrettyPrinting().create().toJson(config);
                Files.writeString(configPath, jsonData);
            } catch (Exception e) {
                LOGGER.error("Failed to create default config file", e);
            }

            return;
        }

        // Read config
        try {
            var jsonData = Files.readString(configPath);
            config = new Gson().fromJson(jsonData, ConfigData.class);
        } catch (Exception e) {
            LOGGER.error("Failed to read config file", e);
        }
    }

    public static final class ConfigData {
        private final boolean allowBreaking;
        private final boolean disableFancyDurability;

        public ConfigData(
                boolean allowBreaking,
                boolean disableFancyDurability
        ) {
            this.allowBreaking = allowBreaking;
            this.disableFancyDurability = disableFancyDurability;
        }

        public boolean allowBreaking() {
            return allowBreaking;
        }

        public boolean disableFancyDurability() {
            return disableFancyDurability;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (ConfigData) obj;
            return this.allowBreaking == that.allowBreaking &&
                    this.disableFancyDurability == that.disableFancyDurability;
        }

        @Override
        public int hashCode() {
            return Objects.hash(allowBreaking, disableFancyDurability);
        }

        @Override
        public String toString() {
            return "ConfigData[" +
                    "allowBreaking=" + allowBreaking + ", " +
                    "disableFancyDurability=" + disableFancyDurability + ']';
        }
    }
}
