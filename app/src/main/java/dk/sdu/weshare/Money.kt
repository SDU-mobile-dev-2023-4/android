package dk.sdu.weshare

class Money(private val cents: Int) {
    override fun toString(): String {
        val leftSide = cents / 100
        val rightSide = cents % 100

        return "$leftSide.$rightSide kr"
    }
}
