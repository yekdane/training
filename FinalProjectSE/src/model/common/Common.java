package model.common;

import java.io.Serializable;

public class Common<T> implements Serializable {
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if(obj==null || getClass()!=obj.getClass())
            return false;
        return getId().equals(((Common) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
