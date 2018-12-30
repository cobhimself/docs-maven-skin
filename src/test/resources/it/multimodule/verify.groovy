// This script verifies that a minimal site contains only the barebones of a site.

import com.jcabi.w3c.ValidatorBuilder
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.jsoup.Jsoup

// Verifies that all the files were created
[
    'target/site/index.html',
    'module/target/site/index.html'
].each {
    def file = new File(basedir, it)
    if (!file.exists()) {
        throw new IllegalStateException(
            "file ${file} doesn't exist"
        )
    }
}

// Acquires the sample HTML content
def html = new File(basedir, 'target/site/index.html').text
def htmlModule = new File(basedir, 'module/target/site/index.html').text

// Validates HTML 5
def htmlResponse = new ValidatorBuilder().html().validate(html)

MatcherAssert.assertThat(
    'There are errors',
    htmlResponse.errors(),
    Matchers.describedAs(htmlResponse.toString(), Matchers.hasSize(0))
)
MatcherAssert.assertThat(
    'There are warnings',
    htmlResponse.warnings(),
    Matchers.describedAs(htmlResponse.toString(), Matchers.hasSize(0))
)

def htmlResponseModule = new ValidatorBuilder().html().validate(htmlModule)
MatcherAssert.assertThat(
    'There are errors',
    htmlResponseModule.errors(),
    Matchers.describedAs(htmlResponseModule.toString(), Matchers.hasSize(0))
)
MatcherAssert.assertThat(
    'There are warnings',
    htmlResponseModule.warnings(),
    Matchers.describedAs(htmlResponseModule.toString(), Matchers.hasSize(0))
)

// Parses HTML
def parsed = Jsoup.parse(html)
def body = parsed.body()
def head = parsed.head()

// Verifies the skin info is included

// Footer link
def div = body.select( 'footer.footer div.row div' ).last()
assert div.html().contains( 'Rendered using' )

// Comments before the head
assert html.contains( 'Rendered using Docs Maven Skin' )
assert html.contains( 'Generated by Apache Maven Doxia' )
