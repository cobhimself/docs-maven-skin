// This script verifies that a minimal site contains only the barebones of a site.

import com.jcabi.w3c.ValidatorBuilder
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.jsoup.Jsoup

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
def titleHeading = body.select( 'h1' ).first()
assert titleHeading.html().contains( 'Page Content' )
assert titleHeading.id().equals( 'page-content' )

def subHeadings = body.select( 'h2' )

// Verifies the first subsection uses the correct text
def firstSubHeading = subHeadings.get(0)
assert firstSubHeading.html().contains( 'Subsection' )
assert firstSubHeading.id().equals( 'subsection' )

// Verifies the second subsection uses the correct text
def secondSubHeading = subHeadings.get(1)
assert secondSubHeading.html().contains( 'Second Subsection' )
assert secondSubHeading.id().equals( 'second-subsection' )

def subSubHeadings = body.select( 'h3' )

// Verifies the first subsection uses the correct text
def firstSubSubHeading = subSubHeadings.first()
assert firstSubSubHeading.html().contains( 'Smaller subsection' )
assert firstSubSubHeading.id().equals( 'smaller-subsection' )

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
