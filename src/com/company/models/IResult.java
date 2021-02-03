package com.company.models;

public interface IResult<T> {

    Result<T> run(T model);

}
