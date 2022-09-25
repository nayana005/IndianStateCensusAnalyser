package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {

    //METHOD TO LOAD THE CSV FILE AND GET
    public int loadIndiaCensusData(String csvFilePath) throws StateCensusAnalyserException {
        int recordCount = 0;
        if (getFileExtension(csvFilePath) != ".csv")
            throw new StateCensusAnalyserException(StateCensusAnalyserException.CensusAnalyserCustomExceptionType.NO_SUCH_TYPE,"No such a type");
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvToBeanBuilder<CSVstateCensus> csvToBeanBuilder = new CsvToBeanBuilder<CSVstateCensus>(reader);
            csvToBeanBuilder.withType(CSVstateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVstateCensus> csvToBean = csvToBeanBuilder
                    .build();
            Iterator<CSVstateCensus> censusCSVIterator = csvToBean.iterator();
            while (censusCSVIterator.hasNext()) {
                System.out.print(recordCount++ + "  ");
                CSVstateCensus censusCSV = censusCSVIterator.next();
                System.out.print("state: " + censusCSV.getState() + ", ");
                System.out.print("population: " + censusCSV.getPopulation() + ", ");
                System.out.print("area: " + censusCSV.getAreaInSqKm() + ", ");
                System.out.print("density: " + censusCSV.getDensityPerSqKm() + ", ");
                System.out.println();
            }

        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.CensusAnalyserCustomExceptionType.WRONG_DELIMITER_OR_HEADER,"File not found");
        }catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.CensusAnalyserCustomExceptionType.FILE_NOT_FOUND, "File not found");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return recordCount;
    }

    private static String getFileExtension(String file) {
        String extension = "";
        try {
            if (file != null) {
                extension = file.substring(file.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }
        return extension;
    }


    public static void main(String[] args) {
        System.out.println("Welcome to Indian States Census Analyser Problem");
    }
}
