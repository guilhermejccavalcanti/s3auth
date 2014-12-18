/**
 * Copyright (c) 2012-2014, s3auth.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the s3auth.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.s3auth.hosts;

import com.jcabi.urn.URN;
import java.net.URI;
import java.util.Random;
import lombok.experimental.Builder;

/**
 * Mocker of {@link User}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.0.1
 */
public final class UserMocker {

    /**
     * The mock.
     */
    private final transient MkUser.MkUserBuilder user = MkUser.builder();

    /**
     * Public ctor.
     */
    public UserMocker() {
        this.withIdentity(
            new URN(
                "facebook",
                Long.toString(Math.abs(new Random().nextLong()))
            )
        );
        this.user.name("John Doe");
        this.user.photo(URI.create("#"));
    }

    /**
     * With provided identity.
     * @param identity The identity
     * @return This object
     */
    public UserMocker withIdentity(final URN identity) {
        this.user.identity(identity);
        return this;
    }

    /**
     * With provided identity.
     * @param identity The identity
     * @return This object
     */
    public UserMocker withIdentity(final String identity) {
        return this.withIdentity(URN.create(identity));
    }

    /**
     * Mock it.
     * @return The user
     */
    public User mock() {
        return this.user.build();
    }

    @Builder
    private static class MkUser implements User {
        /**
         * The User identity.
         */
        private final URN identity;
        /**
         * The User name.
         */
        private final String name;
        /**
         * The User photo.
         */
        private final URI photo;

        @Override
        public URN identity() {
            return this.identity;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public URI photo() {
            return this.photo;
        }
    }
}
