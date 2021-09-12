package biv.service;

import lombok.NonNull;

public interface BaseDomainService<T, ID> {

    boolean create(@NonNull T domain);

    T read(@NonNull ID id);

    void update(@NonNull T domain);

    void delete(@NonNull ID id);
}
