package com.example.jwt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jwt.ui.theme.JWTTheme
import io.jsonwebtoken.Jwts
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import java.io.StringReader
import java.security.PublicKey
import java.security.Security
import java.security.interfaces.RSAPublicKey

class MainActivity : ComponentActivity() {

    companion object {
        init {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
            Security.addProvider(BouncyCastleProvider())
        }
    }

    val publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu1SU1LfVLPHCozMxH2Mo\n" +
            "4lgOEePzNm0tRgeLezV6ffAt0gunVTLw7onLRnrq0/IzW7yWR7QkrmBL7jTKEn5u\n" +
            "+qKhbwKfBstIs+bMY2Zkp18gnTxKLxoS2tFczGkPLPgizskuemMghRniWaoLcyeh\n" +
            "kd3qqGElvW/VDL5AaWTg0nLVkjRo9z+40RQzuVaE8AkAFmxZzow3x+VJYKdjykkJ\n" +
            "0iT9wCS0DRTXu269V264Vf/3jvredZiKRkgwlL9xNAwxXFg0x/XFw005UWVRIkdg\n" +
            "cKWTjpBP2dPwVZ4WWC+9aGVd+Gyn1o0CLelf4rEjGoXbAAEgAqeGUxrcIlbjXfbc\n" +
            "mwIDAQAB\n" +
            "-----END PUBLIC KEY-----"

    val jwtPS512 =  "eyJhbGciOiJQUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.GNhJz8YNyIT2e2kpO7jkH8K7z8lCP1Tsn3YO5_W_7BxB0U6VdoOK_1-l3Y8gWQV-XNrObDFsAdpvudTNkF_cQzZO3I3_6LdjU3iQ4NSTbJwaiaDzyaARC8hIWa55K7Hfz_m9btKOahJpqiiZ5RZNeCVC9VII4uxbuZozfC8r0aXsnmd97TH2vdpIcnzuADH_Cu_AhUSF2C1Bsk4RZe6wf_WmopP48WD3EUmZYvnaSuACtZrN3jRIymcvmtQWWOkFlAjHxjSyKYO33MgpPh1wI_jLfOUZY0S8gxylbd8LK3b0YE0jkpjOznsY8M03dAAS8V_pfzLOLB2yMrRR9e0mLA"
    val jwtRSA512 = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.jYW04zLDHfR1v7xdrW3lCGZrMIsVe0vWCfVkN2DRns2c3MN-mcp_-RE6TN9umSBYoNV-mnb31wFf8iun3fB6aDS6m_OXAiURVEKrPFNGlR38JSHUtsFzqTOj-wFrJZN4RwvZnNGSMvK3wzzUriZqmiNLsG8lktlEn6KA4kYVaM61_NpmPHWAjGExWv7cjHYupcjMSmR8uMTwN5UuAwgW6FRstCJEfoxwb0WKiyoaSlDuIiHZJ0cyGhhEmmAPiCwtPAwGeaL1yZMcp0p82cpTQ5Qb-7CtRov3N4DcOHgWYk6LomPR5j5cCkePAz87duqyzSMpCB0mCOuE3CU2VMtGeQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JWTTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        try {
            val parser = Jwts.parser().verifyWith(stringToPublicKey()).build()
            val test = parser.parse(jwtPS512)
            Log.i("", "$test")
        } catch (e: Throwable) {
            Log.e("", "", e)
        }
    }

    private fun stringToPublicKey(): PublicKey {
        val parser = PEMParser(StringReader(publicKey))
        val converter = JcaPEMKeyConverter()
        val publicKeyInfo: SubjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(parser.readObject())
        return (converter.getPublicKey(publicKeyInfo) as RSAPublicKey)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JWTTheme {
        Greeting("Android")
    }
}