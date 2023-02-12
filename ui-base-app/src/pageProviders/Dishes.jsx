import React from 'react';
import PageAccessValidator from 'components/PageAccessValidator';
import DishesPage from 'pages/Dishes';
import PageContainer from 'components/PageContainer';

const Dishes = () => (
    <PageAccessValidator>
        <PageContainer>
            <DishesPage />
        </PageContainer>
    </PageAccessValidator>
);

export default Dishes;
