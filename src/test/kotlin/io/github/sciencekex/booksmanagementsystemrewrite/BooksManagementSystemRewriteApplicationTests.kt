package io.github.sciencekex.booksmanagementsystemrewrite

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest(classes = [BooksManagementSystemRewriteApplication::class])
class BooksManagementSystemRewriteApplicationTests {

    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    fun contextLoads() {
        assertThat(context).isNotNull
    }
}
