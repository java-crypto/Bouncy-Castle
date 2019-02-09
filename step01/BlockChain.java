package step01;

// source: https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
// https://github.com/CryptoKass/NoobChain-Tutorial-Part-1
// benötigt gson-2.8.5.jar
// Schritt 1: Erstellung einer einfachen Blockchain

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class BlockChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();

	public static void main(String[] args) {
		System.out.println("J01 BlockChain für Anfänger Stufe 01");

		int blockNumber;

		System.out.println("\nAnzahl Einträge in der blockchain:" + blockchain.size());

		// datensatz 1
		blockNumber = 1;
		System.out.println("\nErgänze den Block " + blockNumber);
		addBlock(new Block(blockNumber, (blockNumber - 1), "Data " + blockNumber)); // genesis block
		// daten der blockchain
		System.out.println("Anzahl Einträge in der blockchain:" + blockchain.size());
		System.out.println("Inhalt der BlockChain:\n" + getJson(blockchain));

		// datensatz 2
		blockNumber = 2;
		System.out.println("\nErgänze den Block " + blockNumber);
		addBlock(new Block(blockNumber, (blockNumber - 1), "Data " + blockNumber));
		// daten der blockchain
		System.out.println("Anzahl Einträge in der blockchain:" + blockchain.size());
		System.out.println("Inhalt der BlockChain:\n" + getJson(blockchain));
	}

	public static void addBlock(Block newBlock) {
		blockchain.add(newBlock);
	}

	// Short hand helper to turn Object into a json string
	public static String getJson(Object o) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
}
