package pro.mikey.justhammers.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import pro.mikey.justhammers.Hammers;

import java.util.Objects;
import java.util.function.Supplier;

public final class DeferredResource<R, T extends R> implements Supplier<T> {
    private final ResourceLocation location;
    private final Supplier<T> creator;

    private T instance;

    public DeferredResource(ResourceLocation location, Supplier<T> creator) {
        this.location = location;
        this.creator = creator;
    }

    public DeferredResource(String location, Supplier<T> creator) {
        this(Hammers.id(location), creator);
    }

    public ResourceLocation location() {
        return location;
    }

    public ResourceKey<R> createKey(ResourceKey<Registry<R>> registryKey) {
        return ResourceKey.create(registryKey, location);
    }

    @Override
    public T get() {
        if (instance == null) {
            instance = creator.get();
        }

        return instance;
    }

    /**
     * Uniqueness is determined by location only.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeferredResource<?, ?> that = (DeferredResource<?, ?>) obj;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "DeferredResource[" +
                "location=" + location + ", " +
                "supplier=" + creator + ']';
    }

}
