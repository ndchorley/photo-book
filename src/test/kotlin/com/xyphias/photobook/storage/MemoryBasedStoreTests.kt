package com.xyphias.photobook.storage

import com.xyphias.photobook.Repository
import com.xyphias.photobook.storage.RepositoryContract

class MemoryBasedStoreTests : RepositoryContract() {
    override val repository: Repository = MemoryBasedStore()
}