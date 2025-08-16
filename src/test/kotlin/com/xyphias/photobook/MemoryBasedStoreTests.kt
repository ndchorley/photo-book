package com.xyphias.photobook

class MemoryBasedStoreTests : RepositoryContract() {
    override val repository: Repository = MemoryBasedStore()
}
