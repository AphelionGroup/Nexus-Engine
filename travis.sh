#!/bin/bash

# author: David Belicza
# Javadoc generator and publisher script for Nexus Engine

# highlight
HGL='\e[93m'
# end of color
NC='\033[0m' 

echo -e "\n${HGL}GENERATING OF JAVA DOCS${NC}\n"

# re-create the directory for the old repo
rm -rf javadoc_old
mkdir javadoc_old

echo -e "\n${HGL}...looking for the nexus-engine-javadoc repo${NC}\n"
# clone the javadoc repo to the previously created directory
cd javadoc_old
git clone https://github.com/Youama/nexus-engine-javadoc.git
cd ..

# remove the local javadoc static files
rm -rf javadoc/apidocs

echo -e "\n${HGL}...create new JAVA doc${NC}\n"
# generate javadoc from the source code
mvn javadoc:javadoc

# copy the old repo headers to the newly generated javadoc
cp javadoc_old/nexus-engine-javadoc/.gitignore javadoc/apidocs/.gitignore
mkdir javadoc/apidocs/.git
cp -r javadoc_old/nexus-engine-javadoc/.git/* javadoc/apidocs/.git/

# remove the iframe from javadoc
cd javadoc/apidocs
rm index.html
cp index-all.html index.html

echo -e "\n${HGL}...publishing the new JAVA doc${NC}\n"
# publish the new docusmentation
git push

echo -e "\n${HGL}JAVA doc has been generated and buplished automatically${NC}\n"
echo -e "\n"
echo -e "2015 ${HGL}Nexus Engine${NC}\n"