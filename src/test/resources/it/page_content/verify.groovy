// This script verifies that a minimal site contains only the barebones of a site.

import com.jcabi.w3c.ValidatorBuilder
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.jsoup.Jsoup

// Verifies that all the files were created
[
    'target/site/index.html',
    'target/site/favicon.ico',
    'target/site/robots.txt',
    'target/site/css/style.min.css',
    'target/site/js/scripts.min.js'
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

// Parses HTML
def parsed = Jsoup.parse(html)
def body = parsed.body()

// Verifies the heading uses the correct text
def titleHeading = body.select( 'h1' )
assert titleHeading.html().contains( 'Page Content' )
assert titleHeading.attr( 'id' ).contains( 'pagecontent' )

def subHeadings = body.select( 'h2' )

// Verifies the first subsection uses the correct text
def firstSubHeading = subHeadings.first()
assert firstSubHeading.html().contains( 'Subsection' )
assert firstSubHeading.attr( 'id' ).contains( 'subsection' )

// Verifies the second subsection uses the correct text
def secondSubHeading = subHeadings.get(1)
assert secondSubHeading.html().contains( 'Second Subsection' )
assert secondSubHeading.attr( 'id' ).contains( 'secondsubsection' )

def subSubHeadings = body.select( 'h3' )

// Verifies the first subsection uses the correct text
def firstSubSubHeading = subSubHeadings.first()
assert firstSubSubHeading.html().contains( 'Smaller subsection' )
assert firstSubSubHeading.attr( 'id' ).contains( 'smallersubsection' )

// Verifies the number of sections is correct
def mainSections = body.select( '> section' )
assert mainSections.size() == 1

// Verifies the number of subsections is correct
def subSections = mainSections.first().select( '> section' )
assert subSections.size() == 2

def subSubSections1 = subSections.get(0).select( '> section' )
assert subSubSections1.size() == 1

def subSubSections2 = subSections.get(1).select( '> section' )
assert subSubSections2.size() == 0