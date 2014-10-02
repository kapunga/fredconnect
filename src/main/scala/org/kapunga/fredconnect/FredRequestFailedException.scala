package org.kapunga.fredconnect

import org.kapunga.fredconnect.FredFailureReason.FredFailureReason

class FredRequestFailedException(reason: FredFailureReason) extends Exception {

}
