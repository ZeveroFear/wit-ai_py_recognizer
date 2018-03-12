
sendMessage("core:add-command", [ tag: "recognition:start-listening" ])

sendMessage("core:set-event-link", [
    eventName: "gui:keyboard-handle",
    commandName: "recognition:start-listening",
    rule: "F5",
    msgData: "2"
])

addMessageListener("recognition:start-listening", { s, t, data ->
    sendMessage("DeskChan:say", "Говори")
    try {
        Process process = ('python Recognize.py ' + data).execute(null, getPluginDirPath().toFile())
        Thread.start {
            process.waitFor()
            if (process.exitValue() == 0)
                sendMessage("gui:raise-user-balloon", [value: process.text])
            else {
                sendMessage("DeskChan:say", "Ой, что-то не получилось. Код ошибки: " + process.exitValue())
                log(process.errorStream.text)
            }
        }
    } catch (Exception e){
        log(e)
    }
})