

cd ChaseLogic
mvn install


cd ..
cd Game
mvn clean compile
mvn exec:java -Dexec.mainClass="edu.school21.ConsoleGame.Game" -Dexec.args="--enemiesCount=3 --wallsCount=10 --size=20 --profile=production"


#you can run game in three mode:
mvn exec:java -Dexec.mainClass="edu.school21.ConsoleGame.Game" -Dexec.args="--enemiesCount=3 --wallsCount=10 --size=20 --profile=production"
mvn exec:java -Dexec.mainClass="edu.school21.ConsoleGame.Game" -Dexec.args="--enemiesCount=2 --wallsCount=3 --size=10 --profile=development"
mvn exec:java -Dexec.mainClass="edu.school21.ConsoleGame.Game" -Dexec.args="--enemiesCount=1 --wallsCount=3 --size=7 --profile=hidden"




#create a JAR 
mvn install

jar tf target/Game-1.0-SNAPSHOT-jar-with-dependencies.jar
Jar ft target/Game-1.0-SNAPSHOT.jar