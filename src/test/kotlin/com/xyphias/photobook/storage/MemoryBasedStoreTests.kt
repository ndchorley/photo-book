package com.xyphias.photobook.storage

class MemoryBasedStoreTests : RepositoryContract() {
    override val repository: Repository = MemoryBasedStore()
}