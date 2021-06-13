package com.bkbwongo.core.ebaasa.base.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bkaaron
 * @created on 27/04/2021
 * @project ebaasa-sms
 */
public interface BaseAPI<T> {

    String APPLICATION_JSON = "application/json";

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    T add(@RequestBody T t);

    @GetMapping(path="/{id}", produces = APPLICATION_JSON)
    T get(@PathVariable("id") Long id);

    @PutMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    T update(T t);

    @DeleteMapping(path="/{id}",produces = APPLICATION_JSON)
    T delete(@PathVariable("id") Long id);

    @GetMapping(path="/all", produces = APPLICATION_JSON)
    List<T> getAll();

}
