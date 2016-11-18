package Exception;

public class NoZooKeeperServerUpException extends Exception {

	public NoZooKeeperServerUpException() {
		this.toString();
	}

	@Override
	public String toString() {

		return "No Zookeeper servers available. Kindly try again after some time";
	}

}
