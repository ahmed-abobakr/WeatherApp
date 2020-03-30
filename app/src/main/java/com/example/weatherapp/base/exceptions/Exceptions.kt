package com.example.weatherapp.base.exceptions

public class ApiException(override val message: String, val errorCode: Int = 0) : Exception(message)

public class NetowrkException(val throwable: Throwable): Exception(throwable)

public class AuthException(val throwable: Throwable): Exception(throwable)

public class UnknownException(val throwable: Throwable): Exception(throwable)