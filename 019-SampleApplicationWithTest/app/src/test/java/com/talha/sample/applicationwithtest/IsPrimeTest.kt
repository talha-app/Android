package com.talha.sample.applicationwithtest

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(Parameterized::class)
class IsPrimeTest(val dataInfo: DataInfo) {
    class DataInfo(val a: Int, val result: Boolean) {
    }

    companion object {
        @JvmStatic
        private fun fillDataInfo(br: BufferedReader, data: MutableCollection<DataInfo>) {
            while (true) {
                val line = br.readLine() ?: break
                val dataInfoStr = line.split(" ", "\t")
                data.add(DataInfo(dataInfoStr[0].toInt(), dataInfoStr[1].toBoolean()))
            }
        }

        @Parameterized.Parameters
        @JvmStatic
        fun provideData(): MutableCollection<DataInfo> {
            val data = ArrayList<DataInfo>()
            Files.newBufferedReader(Paths.get("setup-prime.txt"))
                    .use {
                        fillDataInfo(it, data)
                    }
            return data
        }
    }

    @Test
    fun testIsPrime() {
        Assert.assertEquals(true, NumberUtil.isPrime(19))
        Assert.assertNotEquals(true, NumberUtil.isPrime(9))
    }
}