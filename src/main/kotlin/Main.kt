
import application.MainApplication
import infra.Translator.*
import infra.comunicator.*

fun main(args: Array<String>) {

       MainApplication(HardcodeComunicator(), USTranslator()).start()

}