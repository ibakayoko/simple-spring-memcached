package net.nelz.simplesm.test;

import static org.junit.Assert.*;

import java.util.List;

import net.nelz.simplesm.test.svc.TestSvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


/**
Copyright (c) 2008, 2009  Nelson Carpentier

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( {DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:META-INF/test-context.xml", "classpath*:simplesm-context.xml" })
public class InvalidateAssignCacheTest {
	@Autowired
	private TestSvc test;

    @Test
    public void test() {
        //final TestSvc test = (TestSvc) context.getBean("testSvc");

        final List<String> result1 = test.getAssignStrings();
        final List<String> result2 = test.getAssignStrings();

        assertEquals(result1.size(), result2.size());
        for (int ix = 0; ix < result1.size(); ix++) {
            assertEquals(result1.get(ix), result2.get(ix));
        }

        test.invalidateAssignStrings();
        final List<String> result3 = test.getAssignStrings();

        // This was wrong before. The 3rd array is supposed to come
        // back different than the ones pulled before the invalidate
        assertFalse(result1.size() == result3.size());
        for (int ix = 0; ix < result1.size(); ix++) {
            assertFalse(result3.contains(result1.get(ix)));
        }                
    }
}