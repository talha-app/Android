package com.talha.todoinfoapplication.repository;

import com.talha.todoinfoapplication.entity.TodoInfo;

import org.csystem.util.data.repository.ICrudRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class TodoInfoRepository implements ICrudRepository<TodoInfo, Long> {

    private static List<TodoInfo> mList = new ArrayList();
    private static int index = 0;

    @Inject
    public TodoInfoRepository() {
    }

    @Override
    public <S extends TodoInfo> S save(S entity) {
        entity.setId(++index);
        mList.add(entity);
        return entity;
    }

    public List<TodoInfo> findByStartDate(LocalDateTime date) {
        return mList.stream().filter(ti -> ti.getStartDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return mList.size();
    }

    @Override
    public Iterable<TodoInfo> findAll() {
        return mList;
    }

    @Override
    public <S extends TodoInfo> Iterable<S> save(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(TodoInfo entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends TodoInfo> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exitsById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<TodoInfo> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<TodoInfo> findById(Long aLong) {
        throw new UnsupportedOperationException();
    }


}
