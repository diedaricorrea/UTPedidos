package com.example.Ejemplo.interfaces;

public interface Operaciones<T> {
    public void add(T data);
    public void update(T data);
    public void delete(int id);
    public T find(int id);
}
