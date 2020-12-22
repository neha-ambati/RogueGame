package rogue;

public class A1Solution {

    /**
     * sends file to Rogue Parser to parse file and displays room.
     * @param args
     */
    public static void main(String[] args) {
      // Hardcoded configuration file location/name
      String configurationFileLocation = "fileLocations.json";
      RogueParser parser = new RogueParser(configurationFileLocation);

      Rogue myGame = new Rogue(parser);

    /*  Player newPlayer = new Player();
      newPlayer.setName("Toasty");
      myGame.setPlayer(newPlayer); */

      myGame.displayAll();
      System.out.println(myGame.displayAll());
    }
}
