// This script verifies that a site contains the bottom navigation.

// The files to test are read
File index = new File( basedir, "target/site/index.html" )

// Verifies that all the files were created
assert index.exists()

// Acquires the sample HTML content
String html = index.getText()

// Verifies the footer columns exist and contain the expected data
assert html.contains( '<dt>General Info</dt>' )
assert html.contains( '<dd><a href="./acquire.html" title="Acquire">Acquire</a></dd>' )
assert html.contains( '<dd><a href="./usage.html" title="Usage">Usage</a></dd>' )

assert html.contains( '<dt>Code</dt>' )
assert html.contains( '<dd><a href="https://github.com/Bernardo-MG/docs-maven-skin" title="SCM" class="link-noted">SCM <span class="note">GitHub</span></a></dd>' )
assert html.contains( '<dd><a href="https://travis-ci.org/Bernardo-MG/docs-maven-skin" title="CI" class="link-noted">CI <span class="note">Travis CI</span></a></dd>' )
assert html.contains( '<dd><a href="https://www.github.com/bernardo-mg/docs-maven-skin/issues" title="Issues" class="link-noted">Issues <span class="note">GitHub</span></a></dd>' )

assert html.contains( '<dt>Releases</dt>' )
assert html.contains( '<dd><a href="https://bintray.com/bernardo-mg/maven/docs-maven-skin/view" title="Bintray">Bintray</a></dd>' )
assert html.contains( '<dd><a href="http://mvnrepository.com/artifact/com.wandrell.maven.skins/docs-maven-skin" title="Maven Central">Maven Central</a></dd>' )