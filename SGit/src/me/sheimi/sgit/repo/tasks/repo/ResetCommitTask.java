package me.sheimi.sgit.repo.tasks.repo;

import me.sheimi.sgit.R;
import me.sheimi.sgit.database.models.Repo;

import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

public class ResetCommitTask extends RepoOpTask {

    private AsyncTaskPostCallback mCallback;

    public ResetCommitTask(Repo repo, AsyncTaskPostCallback callback) {
        super(repo);
        mCallback = callback;
        setSuccessMsg(R.string.success_reset);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return reset();
    }

    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (mCallback != null) {
            mCallback.onPostExecute(isSuccess);
        }
    }

    public boolean reset() {
        try {
            mRepo.getGit().reset().setMode(ResetCommand.ResetType.HARD).call();
        } catch (GitAPIException e) {
            setException(e);
            return false;
        }
        return true;
    }
}
