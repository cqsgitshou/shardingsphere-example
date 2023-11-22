package com.sharding.jdbc.demo;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class Tests {

	public static void main(String[] args) {
		DefaultKeyGenerator generator = new DefaultKeyGenerator();

		System.out.println(generator.generateKey().longValue());;
	}
}
