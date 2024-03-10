import React from 'react';
import clsx from 'clsx';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import useBaseUrl from '@docusaurus/useBaseUrl';
import styles from './styles.module.css';

const features = [
    {
        imageUrl: 'img/start-quickly-and-scale.svg',
        title: <>Start Quickly and Scale</>,
        description: (
            <>
                Build your first GraphQL server in minutes and
                scale to production loads.
            </>
        ),
    }
];



function Home() {
    const context = useDocusaurusContext();
    const {siteConfig = {}} = context;
    const img = useBaseUrl('img/logo.png');
    return (
        <Layout
            title={`A GraphQL implementation built with Apache Pekko`}
            description="SymphonyQL is a GraphQL implementation built with Apache Pekko.">
            <header className={clsx('hero hero--primary', styles.heroBanner)}>
                <div className="container">
                    <img src={img} width="10%"/>
                    <p className="hero__subtitle">{siteConfig.tagline}</p>
                    <div className={styles.buttons}>
                        <Link
                            className={clsx(
                                styles.indexCtasGetStartedButton,
                            )}
                            to={useBaseUrl('docs/')}>
                            Get Started
                        </Link>
                    </div>
                </div>
            </header>
            <main>
            </main>
        </Layout>
    );
}

export default Home;