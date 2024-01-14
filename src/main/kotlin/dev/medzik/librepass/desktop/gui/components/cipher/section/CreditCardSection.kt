package dev.medzik.librepass.desktop.gui.components.cipher.section

import dev.medzik.librepass.desktop.gui.components.cipher.entry.CipherEntry
import dev.medzik.librepass.desktop.gui.components.cipher.entry.CopyCipherEntry
import dev.medzik.librepass.desktop.gui.components.cipher.entry.PasswordCipherEntry
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import javafx.animation.RotateTransition
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.transform.Rotate

const val BULLET = "\u2022"

class CreditCardSection : CipherSection() {
    private var creditCardPane = VBox()
    private var creditCardReversedPane = VBox()

    private val nameEntry = CipherEntry(tr("cipher.creditcard.name"))
    private val cardholderEntry = CopyCipherEntry(tr("cipher.creditcard.cardholder"))
    private val cardNumberEntry = PasswordCipherEntry(tr("cipher.creditcard.number"))
    private val expMonthEntry = CopyCipherEntry(tr("cipher.creditcard.expiration.month"))
    private val expYearEntry = CopyCipherEntry(tr("cipher.creditcard.expiration.year"))
    private val securityCode = PasswordCipherEntry(tr("cipher.creditcard.securitycode"))

    private val cardBadgeController: CardBadgeController = CardBadgeController()

    private lateinit var currentCipher: Cipher

    init {
        Fxml.loadComponent("/fxml/components/cipher/credit-card.fxml", cardBadgeController, creditCardPane)
        Fxml.loadComponent("/fxml/components/cipher/credit-card-reversed.fxml", cardBadgeController, creditCardReversedPane)

        children.remove(nameLabel)

        beforePane.children.add(creditCardPane)

        addEntry(nameEntry)
        addEntry(cardholderEntry)
        addEntry(cardNumberEntry)
        addEntry(expMonthEntry)
        addEntry(expYearEntry)
        addEntry(securityCode)

        cardNumberEntry.onToggle = {
            cardBadgeController.cardNumber.text =
                if (cardNumberEntry.isToggled()) {
                    currentCipher.cardData?.number.toString().chunked(4).joinToString(" ")
                } else {
                    BULLET.repeat(currentCipher.cardData?.number.toString().length).chunked(4).joinToString(" ")
                }
        }

        securityCode.onToggle = {
            cardBadgeController.securityNumber.text =
                if (securityCode.isToggled()) {
                    "CVV ${currentCipher.cardData?.code!!}"
                } else {
                    "CVV ${BULLET.repeat(currentCipher.cardData?.code!!.length)}"
                }
        }

        creditCardPane.setOnMouseClicked {
            reverseCardBadge(creditCardPane, creditCardReversedPane)
        }
        creditCardReversedPane.setOnMouseClicked {
            reverseCardBadge(creditCardReversedPane, creditCardPane)
        }
    }

    private var transitionRun = false

    private fun reverseCardBadge(
        first: Pane,
        second: Pane
    ) {
        if (transitionRun)
            return

        transitionRun = true

        val rotateTransition =
            RotateTransition().apply {
                node = first
                axis = Rotate.Y_AXIS
                byAngle = 90.0
            }

        rotateTransition.setOnFinished {
            val reversedRotateTransition =
                RotateTransition().apply {
                    node = second
                    axis = Rotate.Y_AXIS
                    byAngle = -90.0
                }

            second.rotationAxis = Rotate.Y_AXIS
            second.rotate = 90.0

            beforePane.children.remove(first)
            beforePane.children.add(second)

            reversedRotateTransition.setOnFinished { transitionRun = false }
            reversedRotateTransition.play()
        }

        rotateTransition.play()
    }

    private fun setCreditCardBadge(cipher: Cipher) {
        cardBadgeController.cardholderName.text = cipher.cardData?.cardholderName
        cardBadgeController.cardNumber.text = BULLET.repeat(currentCipher.cardData?.number.toString().length).chunked(4).joinToString(" ")
        cardBadgeController.securityNumber.text = "CVV ${BULLET.repeat(currentCipher.cardData?.code!!.length)}"

        val expMonth = cipher.cardData?.expMonth!!.toInt()
        val expYear = cipher.cardData?.expYear!!.toInt()
        cardBadgeController.expDate.text = String.format("%02d/%02d", expMonth, expYear % 100)
    }

    fun setCipher(cipher: Cipher) {
        currentCipher = cipher

        nameEntry.setValue(cipher.cardData?.name!!)
        cardholderEntry.setValue(cipher.cardData?.cardholderName!!)
        cardNumberEntry.setValue(cipher.cardData?.number!!, true)
        expMonthEntry.setValue(cipher.cardData?.expMonth.toString())
        expYearEntry.setValue(cipher.cardData?.expYear.toString())
        securityCode.setValue(cipher.cardData?.code.toString(), true)

        setCreditCardBadge(cipher)
    }
}

class CardBadgeController {
    @FXML
    lateinit var cardNumber: Label

    @FXML
    lateinit var cardholderName: Label

    @FXML
    lateinit var expDate: Label

    @FXML
    lateinit var securityNumber: Label
}
