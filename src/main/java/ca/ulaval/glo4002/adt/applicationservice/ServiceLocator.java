package ca.ulaval.glo4002.adt.applicationservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ServiceLocator {
    INSTANCE;

    private Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void register(Class<T> contract, T service) {
        if (instances.containsKey(contract)) {
            throw new ServiceAlreadyRegisteredException(contract);
        }
        instances.put(contract, service);
    }

	@SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> contract) {
		T service = (T) instances.get(contract);
        return Optional.ofNullable(service).orElseThrow(() -> new UnknownServiceResolvedException(contract));
    }
}