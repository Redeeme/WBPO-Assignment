package sk.doxxbet.doxxbetui.wpbo_assignment

sealed class Resource<T>(
    val type: ResourceType,
    val data: T? = null,
    val message: String? = null
) {

    fun isSuccess() = type == ResourceType.SUCCESS

    fun isError() = type == ResourceType.ERROR

    class Success<T>(
        data: T?
    ) : Resource<T>(type = ResourceType.SUCCESS, data = data)

    class Error<T>(
        message: String? = null,
    ) : Resource<T>(
        type = ResourceType.ERROR,
        message = message
    )
}

enum class ResourceType {
    SUCCESS, ERROR
}
