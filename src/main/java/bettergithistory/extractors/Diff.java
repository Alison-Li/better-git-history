package bettergithistory.extractors;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * For interfacing with java-diff-utils
 * Source: https://github.com/java-diff-utils/java-diff-utils
 */
public class Diff {
    /**
     * Get the diff between two files using java-diff-utils.
     * @throws IOException
     */
    public static void getDiff(int leftVer, int rightVer) throws IOException {
        List<String> left = Files.readAllLines(Paths.get(new File(String.format("temp/ver%d.java", leftVer)).getPath()));
        List<String> right = Files.readAllLines(Paths.get(new File(String.format("temp/ver%d.java", rightVer)).getPath()));
        Patch<String> patch = DiffUtils.diff(left, right);
        for (AbstractDelta<String> delta : patch.getDeltas()) {
            System.out.println(delta);
        }
    }

    /**
     * Get the diff in a unified file format and apply it as the patch to a given text.
     */
    public static void applyPatch(int leftVer, int rightVer) throws PatchFailedException, IOException {
        List<String> original = Files.readAllLines(Paths.get(new File(String.format("temp/ver%d.java", leftVer)).getPath()));
        List<String> patched = Files.readAllLines(Paths.get(new File(String.format("temp/ver%d.java", rightVer)).getPath()));
        // At first, parse the unified diff file and get the patch
        Patch<String> patch = UnifiedDiffUtils.parseUnifiedDiff(patched);
        // Then apply the computed patch to the given text
        List<String> result = DiffUtils.patch(original, patch);
        System.out.println(result);
    }
}
