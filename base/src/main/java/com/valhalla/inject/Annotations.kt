package com.valhalla.inject

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class SimpleDateFormatter

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class DefaultDateTimeFormatter

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class MainApplicationScope
