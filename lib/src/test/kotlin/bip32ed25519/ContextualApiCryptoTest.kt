/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package bip32ed25519

import cash.z.ecc.android.bip39.Mnemonics.MnemonicCode
import kotlin.collections.component1
import kotlin.test.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

// Helper function to convert a string of comma separated numbers to a byte array (to save space)
fun helperStringToByteArray(input: String): ByteArray {
    return input.split(",").map { it.trim().toInt() }.toIntArray().map { it.toByte() }.toByteArray()
}

val seedArray =
        helperStringToByteArray(
                "58,255,45,180,22,184,149,236,60,249,164,248,209,233,112,188,152,25,146,14,123,244,74,94,53,4,119,175,14,245,87,177,81,27,9,134,222,191,120,221,56,199,197,32,205,68,255,124,114,49,97,143,149,142,33,239,2,80,115,58,140,25,21,234"
        )

class ContextualApiCryptoTest {

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    internal class UnitTestSuite {

        private lateinit var c: ContextualApiCrypto

        @BeforeAll
        fun setup() {
            c = ContextualApiCrypto(seedArray)
        }

        @Test
        fun hardenTest() {
            assert(c.harden(0u) == 2147483648u) { "harden(0) and 2147483648 are not equal" }
            assert(c.harden(1u) == 2147483649u) { "harden(1) and 2147483648 are not equal" }
            assert(c.harden(44u) == 2147483692u) { "harden(44) and 2147483648 are not equal" }
            assert(c.harden(283u) == 2147483931u) { "harden(283) and 2147483648 are not equal" }
        }

        @Test
        fun deriveNonHardenedTest() {
            val kl =
                    helperStringToByteArray(
                            "168,186,128,2,137,34,217,252,250,5,92,120,174,222,85,181,197,117,188,216,213,165,49,104,237,244,95,54,217,236,143,70"
                    )
            val cc =
                    helperStringToByteArray(
                            "121,107,146,6,236,48,225,66,233,75,121,10,152,128,91,249,153,4,43,85,4,105,99,23,78,230,206,226,208,55,89,70"
                    )
            val index = 0u

            val deriveNonHardenedExpectedOutcomeZZ =
                    helperStringToByteArray(
                            "79,57,235,234,215,9,72,57,157,32,34,226,81,95,29,115,250,66,232,187,16,193,209,254,140,127,122,242,224,69,122,166,31,223,82,170,49,164,3,115,96,128,159,63,116,37,118,15,167,94,148,38,50,10,126,70,3,86,36,78,199,91,146,54"
                    )
            val deriveNonHardenedExpectedOutcomeChildChainCode =
                    helperStringToByteArray(
                            "98,42,235,140,228,232,27,136,136,143,220,220,32,187,77,47,254,209,231,13,224,226,108,113,167,234,93,101,160,32,37,152,216,141,148,178,77,222,78,201,150,148,186,65,223,76,237,113,104,229,170,167,224,222,193,99,251,94,222,14,82,185,232,206"
                    )

            val (zProduced, cccProduced) = c.deriveNonHardened(kl, cc, index)

            assert(zProduced.contentEquals(deriveNonHardenedExpectedOutcomeZZ)) {
                "zProduced and deriveNonHardenedExpectedOutcomeZZ are not equal"
            }

            assert(cccProduced.contentEquals(deriveNonHardenedExpectedOutcomeChildChainCode)) {
                "ccProduced and deriveNonHardenedExpectedOutcomeChainCode are not equal"
            }
        }

        @Test
        fun derivedHardenedTest() {
            val c = ContextualApiCrypto(seedArray)

            val kl =
                    helperStringToByteArray(
                            "168,186,128,2,137,34,217,252,250,5,92,120,174,222,85,181,197,117,188,216,213,165,49,104,237,244,95,54,217,236,143,70"
                    )

            val kr =
                    helperStringToByteArray(
                            "148,89,43,75,200,146,144,117,131,226,38,105,236,223,27,4,9,169,243,189,85,73,242,221,117,27,81,54,9,9,205,5"
                    )

            val cc =
                    helperStringToByteArray(
                            "121,107,146,6,236,48,225,66,233,75,121,10,152,128,91,249,153,4,43,85,4,105,99,23,78,230,206,226,208,55,89,70"
                    )
            val index = c.harden(44u)

            val deriveHardenedExpectedOutcomeZZ =
                    helperStringToByteArray(
                            "241,155,222,63,177,102,52,174,88,241,56,59,144,16,74,143,9,66,66,43,208,144,253,154,211,54,107,135,59,57,54,101,184,111,121,207,178,74,118,177,0,10,69,137,96,97,246,116,206,37,118,201,90,48,254,232,249,234,191,143,116,13,40,109"
                    )
            val deriveHardenedExpectedOutcomeChildChainCode =
                    helperStringToByteArray(
                            "113,159,183,57,127,174,86,11,68,82,114,215,136,191,242,88,45,11,66,160,140,77,60,25,130,238,210,239,247,55,117,240,141,123,149,66,11,250,54,180,175,41,166,195,76,15,154,235,246,49,203,70,79,22,94,165,138,89,21,152,23,108,180,148"
                    )

            val (zProduced, cccProduced) = c.deriveHardened(kl, kr, cc, index)

            assert(zProduced.contentEquals(deriveHardenedExpectedOutcomeZZ)) {
                "zProduced and deriveHardenedExpectedOutcomeZZ are not equal"
            }

            assert(cccProduced.contentEquals(deriveHardenedExpectedOutcomeChildChainCode)) {
                "ccProduced and deriveHardenedExpectedOutcomeChildChainCode are not equal"
            }
        }

        @Test
        fun keyGenAcc00Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "98,254,131,43,122,209,5,68,190,131,55,166,112,67,94,80,100,174,74,102,231,123,215,137,9,118,91,70,181,118,166,243"
                    )
            // derive key m'/44'/283'/0'/0/0"
            val derivedPrivate = c.keyGen(KeyContext.Address, 0u, 0u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenAcc01Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "83,4,97,0,46,172,206,192,199,181,121,89,37,170,16,74,127,180,95,133,239,10,169,91,187,91,233,59,111,133,55,173"
                    )
            // derive key m'/44'/283'/0'/0/1"
            val derivedPrivate = c.keyGen(KeyContext.Address, 0u, 1u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenAcc02Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "34,129,200,27,238,4,238,3,159,164,130,194,131,84,28,106,176,108,131,36,219,111,28,197,156,104,37,46,29,88,188,179"
                    )
            // derive key m'/44'/283'/0'/0/2
            val derivedPrivate = c.keyGen(KeyContext.Address, 0u, 2u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenAcc10Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "158,18,100,63,108,0,104,220,245,59,4,218,206,214,248,193,169,10,210,28,149,74,102,223,65,64,215,147,3,22,106,103"
                    )
            // derive key m'/44'/283'/1'/0/1"
            val derivedPrivate = c.keyGen(KeyContext.Address, 1u, 0u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenAcc11Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "25,254,250,164,39,200,166,251,76,248,11,184,72,233,192,195,122,162,191,76,177,156,245,172,149,21,186,30,109,152,140,186"
                    )
            // derive key m'/44'/283'/1'/0/1"
            val derivedPrivate = c.keyGen(KeyContext.Address, 1u, 1u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenAcc21Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "138,93,223,98,213,26,44,80,229,29,186,212,99,67,86,204,114,49,74,129,237,217,23,172,145,218,150,71,122,159,181,176"
                    )
            // derive key m'/44'/283'/2'/0/1
            val derivedPrivate = c.keyGen(KeyContext.Address, 2u, 1u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenAcc30Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "35,88,224,242,180,101,171,62,143,85,19,157,131,22,101,77,75,227,158,187,34,54,125,54,64,159,208,42,32,176,224,23"
                    )
            // derive key m'/44'/283'/3'/0/0"
            val derivedPrivate = c.keyGen(KeyContext.Address, 3u, 0u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenId00Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "182,215,238,165,175,10,216,62,223,67,64,101,158,114,240,234,43,69,102,222,31,195,182,58,64,164,37,170,190,190,94,73"
                    )
            // derive key m'/44'/0'/0'/0/0
            val derivedPrivate = c.keyGen(KeyContext.Identity, 0u, 0u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenId01Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "181,206,198,118,197,162,18,158,209,190,66,35,162,112,36,57,187,178,70,47,215,123,67,242,126,47,121,253,25,74,48,162"
                    )
            // derive key m'/44'/0'/0'/0/1
            val derivedPrivate = c.keyGen(KeyContext.Identity, 0u, 1u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenId02Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "67,94,94,52,70,67,29,70,37,114,171,238,27,139,173,184,134,8,144,106,106,242,123,132,151,188,207,213,3,237,182,254"
                    )

            // derive key m'/44'/0'/0'/0/2
            val derivedPrivate = c.keyGen(KeyContext.Identity, 0u, 2u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenId10Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "191,99,190,131,255,249,188,157,10,235,194,49,213,3,66,17,14,82,32,36,126,80,222,55,107,71,225,84,181,211,42,62"
                    )
            // derive key m'/44'/0'/1'/0/0
            val derivedPrivate = c.keyGen(KeyContext.Identity, 1u, 0u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenId12Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "70,149,142,118,219,21,21,127,64,18,39,248,172,189,183,9,36,93,202,5,85,200,232,95,86,176,210,5,46,131,77,6"
                    )
            // derive key m'/44'/0'/1'/0/2"
            val derivedPrivate = c.keyGen(KeyContext.Identity, 1u, 2u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }

        @Test
        fun keyGenId21Test() {
            val expectedKeyOutput =
                    helperStringToByteArray(
                            "237,177,15,255,36,164,116,93,245,47,26,10,177,174,113,179,117,45,1,156,140,36,55,212,106,184,200,230,52,167,76,212"
                    )
            // derive key m'/44'/0'/2'/0/1
            val derivedPrivate = c.keyGen(KeyContext.Identity, 2u, 1u)
            assert(derivedPrivate.contentEquals(expectedKeyOutput)) {
                "derivedPrivate and expectedKeyOutput are not equal"
            }
        }
    }

    @Test
    fun fromSeedTest() {

        // 58,255,45,180,22,184,149,236,60,249,164,248,209,233,112,188,152,25,146,14,123,244,74,94,53,4,119,175,14,245,87,177,81,27,9,134,222,191,120,221,56,199,197,32,205,68,255,124,114,49,97,143,149,142,33,239,2,80,115,58,140,25,21,234

        //////////
        val seed =
                MnemonicCode(
                        "salon zoo engage submit smile frost later decide wing sight chaos renew lizard rely canal coral scene hobby scare step bus leaf tobacco slice".toCharArray()
                )

        // }
        //
        // For the seed above, the entropy is:
        // -66,-97,-3,41,108,12,-54,-70,-33,81,-58,-5,-71,4,-103,91,24,45,106,-56,65,-127,-64,-115,-117,1,106,-79,-18,-3,120,-50]
        // However, in the typescript tests, what's passed into fromSeed function is the array
        // below. So how do we go from entropy above the value below?
        //////////

        // TODO: produce seedArray from seed using bip39 lib
        val c = ContextualApiCrypto(seedArray)

        val rootKey =
                c.fromSeed(seedArray) // Need to figure out how to go from phrase/entropy to that
        val fromSeedExpectedOutput =
                helperStringToByteArray(
                        "168,186,128,2,137,34,217,252,250,5,92,120,174,222,85,181,197,117,188,216,213,165,49,104,237,244,95,54,217,236,143,70,148,89,43,75,200,146,144,117,131,226,38,105,236,223,27,4,9,169,243,189,85,73,242,221,117,27,81,54,9,9,205,5,121,107,146,6,236,48,225,66,233,75,121,10,152,128,91,249,153,4,43,85,4,105,99,23,78,230,206,226,208,55,89,70"
                )

        assert(rootKey.contentEquals(fromSeedExpectedOutput)) {
            "rootKey and fromSeedExpectedOutput are not equal"
        }

        assert(rootKey.size == 96) { "rootKey size is not 96" }
    }
}
