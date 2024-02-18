
package cz.kotox.common.core.logging

interface LogService {
  fun logNonFatalCrash(throwable: Throwable)
}
