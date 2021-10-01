package be.dist.notiflow


object NotiflowPreference : BasePreference("HashupPreference") {

    private val channelKey = "Key^^channel"
    var channel: String
        get() = getString(channelKey, "")
        set(value) = setString(channelKey, value)

    private val debugKey = "Key^^debug"
    var debug: Boolean
        get() = getBoolean(debugKey, false)
        set(value) = setBoolean(debugKey, value)

    private val botKey = "Key^^bot"
    var bot: Boolean
        get() = getBoolean(botKey, true)
        set(value) = setBoolean(botKey, value)

    private val serverUrlKey = "Key^^serverUrlKey"
    var serverUrl: String
        get() = getString(serverUrlKey, "")
        set(value) = setString(serverUrlKey, value)

}
