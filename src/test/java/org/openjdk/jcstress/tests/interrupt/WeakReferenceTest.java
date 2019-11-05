/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jcstress.tests.interrupt;

import org.openjdk.jcstress.annotations.*;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

@JCStressTest(Mode.Termination)
@Outcome(id = "TERMINATED", expect = Expect.ACCEPTABLE, desc = "The thread had sucessfully terminated.")
@Outcome(id = "STALE",      expect = Expect.FORBIDDEN,  desc = "Thread had failed to respond.")
@Ref("http://altair.cs.oswego.edu/pipermail/concurrency-interest/2012-August/009654.html")
@Ref("http://cs.oswego.edu/pipermail/concurrency-interest/attachments/20120808/cad656d6/attachment.html")
@Ref("http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7190310")
@Ref("http://mail.openjdk.java.net/pipermail/hotspot-compiler-dev/2012-August/008190.html")
@State
public class WeakReferenceTest {

    public volatile Object referent;
    public final WeakReference<Object> ref;
    public final ReferenceQueue<Object> refQueue;

    public WeakReferenceTest() {
        referent = new Object();
        refQueue = new ReferenceQueue<>();
        ref = new WeakReference<>(referent, refQueue);
    }


    @Actor
    public void actor1() {
        while (ref.get() != null) {
            // burn!
        }
    }

    @Signal
    public void signal() throws InterruptedException {
        referent = null;

        // should eventually complete, not testing here
        while (refQueue.poll() != ref) {
            System.gc();
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

}
