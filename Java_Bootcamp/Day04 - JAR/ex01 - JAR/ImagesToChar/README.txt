# from this project folder (Java_Bootcamp.Day04-1/src/ex00/ImagesToChar)


mkdir target

javac -d target src/java/edu/school21/printer/*/*.java

jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target edu/school21/printer/app/App.class -C target edu/school21/printer/logic/Logic.class -C src resources/it.bmp

jar tf target/images-to-chars-printer.jar           #optional - check the contents of JAR

unzip target/images-to-chars-printer.jar resources/it.bmp -d target

java -jar target/images-to-chars-printer.jar . $






# create JAR file in target folder
# c option - indicate we are creating a file
# f option - specify the file
# e entry point - without manifest
# m manifest