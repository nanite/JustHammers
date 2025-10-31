package pro.mikey.justhammers.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.mikey.justhammers.Hammers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public enum SimpleJsonConfig {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJsonConfig.class);

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final String CONFIG_FILE_NAME = "justhammers.json";
    private static final Path CONFIG_FILE = Hammers.XPLAT.configDir().resolve(CONFIG_FILE_NAME);

    private List<CommentedEntry> entries = new LinkedList<>();

    public final CommentedEntry disabledDurabilityTooltip = create(
            "disabledDurabilityTooltip",
            new JsonPrimitive(false),
            "Set to true to disable the durability tooltip on hammers"
    );

    public final CommentedEntry durabilityRepairPercentage = create(
            "durabilityRepairPercentage",
            new JsonPrimitive(33.33D),
            "The percentage of durability restored per repair item"
    );

    public final CommentedEntry durabilityRepairPercentageNetherite = create(
            "durabilityRepairPercentageNetherite",
            new JsonPrimitive(100D),
            "The percentage of durability restored per repair item for netherite hammers"
    );

    public final CommentedEntry allowHammerBreaking = create(
            "allowHammerBreaking",
            new JsonPrimitive(false),
            "Set to true to allow hammers to break when running out of durability"
    );

    private JsonObject config;

    public void load() {
        if (Files.notExists(CONFIG_FILE)) {
            writeDefault();
        }

        try {
            config = GSON.fromJson(Files.newBufferedReader(CONFIG_FILE), JsonObject.class);
            addMissingEntries();
        } catch (Exception e) {
            LOGGER.error("Failed to load config", e);
            writeDefault();
        }
    }

    public void writeDefault() {
        config = new JsonObject();

        for (CommentedEntry entry : entries) {
            entry.write(config);
        }

        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            Files.write(CONFIG_FILE, GSON.toJson(config).getBytes());
        } catch (Exception e) {
            LOGGER.error("Failed to write default config", e);
        }
    }

    public void addMissingEntries() {
        var changed = false;
        for (CommentedEntry entry : entries) {
            if (!config.has(entry.key)) {
                entry.write(config);
                changed = true;
            }
        }

        if (!changed) {
            return;
        }

        try {
            Files.write(CONFIG_FILE, GSON.toJson(config).getBytes());
        } catch (Exception e) {
            LOGGER.error("Failed to write default config", e);
        }
    }

    private CommentedEntry create(String key, JsonPrimitive defaultValue, String comment) {
        CommentedEntry commentedEntry = new CommentedEntry(this, key, defaultValue, comment);
        entries.add(commentedEntry);
        return commentedEntry;
    }

    public static class CommentedEntry implements Supplier<JsonPrimitive> {
        private final SimpleJsonConfig parent;

        private final String key;
        private final JsonPrimitive defaultValue;
        private final String comment;
        private JsonPrimitive value;

        public CommentedEntry(
                SimpleJsonConfig parent,
                String key,
                JsonPrimitive defaultValue,
                String comment
        ) {
            this.parent = parent;
            this.key = key;
            this.defaultValue = defaultValue;
            this.comment = comment;
        }

        @Override
        public JsonPrimitive get() {
            if (value == null) {
                var obj = this.parent.config.getAsJsonObject(key);
                if (obj == null) {
                    value = defaultValue;
                } else {
                    value = obj.getAsJsonPrimitive("value");
                }

                if (value == null) {
                    value = defaultValue;
                }
            }

            return value;
        }

        public void write(JsonObject object) {
            JsonObject holder = new JsonObject();
            holder.add("value", defaultValue);
            holder.addProperty("_comment", comment);

            object.add(key, holder);
        }
    }
}
