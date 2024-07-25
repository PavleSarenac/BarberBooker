package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.ac.bg.etf.barberbooker.data.room.repositories.ClientRepository
import javax.inject.Inject

@HiltViewModel
class ClientViewBarberProfileViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {



}