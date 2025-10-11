package rs.ac.bg.etf.barberbooker.data.retrofit.utils

import okhttp3.Authenticator
import okhttp3.Response
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.JwtAuthenticationData
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.JwtAuthenticationRepository

class JwtAuthenticator(
    private val jwtAuthenticationRepository: JwtAuthenticationRepository
) : Authenticator {
    override fun authenticate(route: okhttp3.Route?, response: Response): okhttp3.Request? {
        if (response.request.header("Authorization")?.startsWith("Bearer") == true &&
            responseCount(response) >= 2
        ) return null

        val refreshToken = JwtAuthenticationUtils.getJwtRefreshToken()
        if (refreshToken.isEmpty()) return null

        val isRefreshSuccessful = runCatching {
            jwtAuthenticationRepository.refresh(JwtAuthenticationData(jwtRefreshToken = refreshToken))
        }.getOrNull() ?: return null

        if (!isRefreshSuccessful) return null

        val jwtAccessToken = JwtAuthenticationUtils.getJwtAccessToken()
        if (jwtAccessToken.isEmpty()) return null

        return response.request.newBuilder()
            .header("Authorization", "Bearer $jwtAccessToken")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
