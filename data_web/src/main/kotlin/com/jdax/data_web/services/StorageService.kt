package com.jdax.data_web.services

import com.jdax.data.utils.logger
import java.nio.file.Path
import kotlin.collections.getValue
import org.slf4j.Logger
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import com.jdax.data_web.services.SavedFileWrapper

private val lazyLogger: Lazy<Logger> = lazy { logger("services.StorageService") }
private val uploadedResourceMap: MutableMap<String, SavedFileWrapper> = mutableMapOf()
private val identifierNumBlocks: Int = 4
private val identifierBlockLength: Int = 4

public fun getAllResourcePaths(): Map<String, SavedFileWrapper> {
    return uploadedResourceMap.toMap()
}

public fun getResource(id: String): Path {
    return uploadedResourceMap.getValue(id).path
}

public fun storeResource(multipartFile: MultipartFile) {
    val baseName: String = getBaseFileName(multipartFile).replace(Regex("\\W+"), "")
    val extension: String = getExtension(multipartFile)
    val path: Path = createTempFile(baseName, extension)
    val resourceId: String = generateIdentifier(identifierNumBlocks, identifierBlockLength)
	multipartFile.transferTo(path)
    uploadedResourceMap.put(resourceId, SavedFileWrapper(resourceId, baseName, path))
}

public fun dropResource(id: String) {
    uploadedResourceMap.remove(id)
}

private fun getBaseFileName(multipartFile: MultipartFile): String {
    val filename: String = multipartFile.originalFilename ?: generateIdentifier(3, 3, "_")
    return filename.substring(0, filename.lastIndexOf("."))
}

private fun getExtension(multipartFile: MultipartFile): String {
    var extension: String? =
            multipartFile.originalFilename?.substring(getBaseFileName(multipartFile).length + 1)
    return ".".plus(extension ?: "txt")
}

private fun generateIdentifier(numBlocks: Int, blockLength: Int, join: String): String {
    // an identifier will follow this scheme:
    // [a-zA-Z0-9]{blockLength} + "-" + ... + [a-zA-Z0-9]{blockLength}
    val allowedCharacters = ('a'..'z') + ('0'..'9') + ('A'..'Z')
    return (1..numBlocks)
            .map({ (1..blockLength).map({ allowedCharacters.random() }).joinToString("") })
            .joinToString(join)
}

private fun generateIdentifier(numBlocks: Int, blockLength: Int): String {
    // an identifier will follow this scheme:
    // [a-zA-Z0-9]{blockLength} + "-" + ... + [a-zA-Z0-9]{blockLength}
    val allowedCharacters = ('a'..'z') + ('0'..'9') + ('A'..'Z')
    return (1..numBlocks)
            .map({ (1..blockLength).map({ allowedCharacters.random() }).joinToString("") })
            .joinToString("-")
}
