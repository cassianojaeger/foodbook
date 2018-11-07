package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecipeFeedbackUserPopulator implements Populator<RecipeFeedbackRegistrationData, RecipeFeedback>
{
    @Resource
    UserDao userDao;

    @Override
    public void populate(RecipeFeedbackRegistrationData source, RecipeFeedback target)
    {
        User user = userDao.findByUsername(source.getUsername());
        target.setUser(user);
    }
}
