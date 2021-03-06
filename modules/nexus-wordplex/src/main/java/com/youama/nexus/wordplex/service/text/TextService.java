package com.youama.nexus.wordplex.service.text;

import com.youama.nexus.core.Log;
import com.youama.nexus.core.base.BaseService;
import com.youama.nexus.wordplex.model.text.TextModel;

/**
 * @author David Belicza
 * @since 0.1.0
 */
public class TextService extends BaseService<TextModel> {

    @Override
    public TextModel add(TextModel textModel) {
        try {
            return super.add(textModel);
        } catch (Exception e) {
            Log.warning(e);
        }

        return null;
    }
}
