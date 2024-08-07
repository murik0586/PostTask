package ru.lion_of_steel.exception

import java.lang.RuntimeException

class AccessDeniedException(msg: String) : RuntimeException(msg)