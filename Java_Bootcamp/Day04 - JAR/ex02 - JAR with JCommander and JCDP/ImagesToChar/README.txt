# from this folder (Java_Bootcamp.Day04-1/src/ex00/ImagesToChar)

mkdir lib
curl -o lib/jcommander-1.82.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar
curl -o lib/JCDP-4.0.2.jar https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar

mkdir target
javac -d target -cp lib/jcommander-1.82.jar:lib/JCDP-4.0.2.jar src/java/edu/school21/printer/*/*.java

cp lib/jcommander-1.82.jar target
cp lib/JCDP-4.0.2.jar target
cp -R src/resources target

cd target
jar -xvf jcommander-1.82.jar
rm -rf META-INF
rm jcommander-1.82.jar

jar -xvf JCDP-4.0.2.jar
rm -rf META-INF
rm -rf JCDP-4.0.2.jar

jar cfmv images-to-chars-printer.jar ../src/manifest.txt . 

java -jar images-to-chars-printer.jar --white=MAGENTA --black=CYAN





# enum constants background color:
    BLACK 
    BLUE 
    CYAN 
    GREEN 
    MAGENTA 
    NONE 
    RED 
    WHITE 
    YELLOW