package com.jdax.data.utils

import kotlin.reflect.full.companionObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public fun logger(className: String): Logger {
    return LoggerFactory.getLogger(className)
}

// Return logger for Java class, if companion object fix the name
fun <T : Any> logger(forClass: Class<T>): Logger {
    return logger(unwrapCompanionClass(forClass).name)
}

// unwrap companion class to enclosing class given a Java Class
fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}

// return logger from extended class (or the enclosing class)
fun <T : Any> T.logger(): Logger {
    return logger(this.javaClass)
}

// return a lazy logger property delegate for enclosing class
fun <R : Any> R.lazyLogger(): Lazy<Logger> {
    return lazy { logger(this.javaClass) }
}

// return a logger property delegate for enclosing class
fun <R : Any> R.injectLogger(): Lazy<Logger> {
    return lazyOf(logger(this.javaClass))
}