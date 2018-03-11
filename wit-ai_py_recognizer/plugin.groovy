
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
        println('python "' + getPluginDirPath().resolve("Recognize.py").toString() +'" ' + data)
        Process process = ('python "' + getPluginDirPath().resolve("Recognize.py").toString() +'" ' + data).execute()
        Thread.start {
            process.waitFor()
            String text = process.text
            if (process.exitValue() == 0)
                sendMessage("gui:raise-user-balloon", [value: text])
            else {
                sendMessage("DeskChan:say", "Ой, что-то не получилось. Код ошибки: " + process.exitValue())
                log(text)
            }
        }
    } catch (Exception e){
        log(e)
    }
})