//package com.TeachingManager.TeachingManager.Repository.User.Institute;
//
//import com.TeachingManager.TeachingManager.domain.Institute;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.function.Function;
//
//@Component
//@RequiredArgsConstructor
//public class InstituteRepositoryImpl implements InstituteRepository{
//    private final EntityManager em;
//    @Override
//    public Optional<Institute> findByEmail(String email) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Institute> findByPk(UUID pk) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Institute> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Institute> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Institute> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Institute getOne(UUID uuid) {
//        return null;
//    }
//
//    @Override
//    public Institute getById(UUID uuid) {
//        return null;
//    }
//
//    @Override
//    public Institute getReferenceById(UUID uuid) {
//        return null;
//    }
//
//    @Override
//    public <S extends Institute> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Institute> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends Institute> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends Institute> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Institute> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Institute> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Institute, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends Institute> S save(S entity) {
//         em.persist(entity);
//         return entity;
//    }
//
//    @Override
//    public <S extends Institute> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Institute> findById(UUID uuid) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(UUID uuid) {
//        return false;
//    }
//
//    @Override
//    public List<Institute> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Institute> findAllById(Iterable<UUID> uuids) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(UUID uuid) {
//
//    }
//
//    @Override
//    public void delete(Institute entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends UUID> uuids) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Institute> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<Institute> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Institute> findAll(Pageable pageable) {
//        return null;
//    }
//}
