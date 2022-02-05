package activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.talha.application.openotheractivity.*
import entity.DeviceInfo

class MainActivity : AppCompatActivity() {
    companion object {
        private const val SECOND_ACTIVITY_REQUEST_CODE = 1
        private const val THIRD_ACTIVITY_REQUEST_CODE = 2
    }

    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextNumber: EditText
    private lateinit var mSwitchOpenStaus: Switch
    private lateinit var mDeviceInfo: DeviceInfo


    private fun initViews() {
        mEditTextName = findViewById(R.id.mainActivityEditTextName)
        mEditTextNumber = findViewById(R.id.mainActivityEditTextNumber)
        mSwitchOpenStaus = findViewById(R.id.mainActivitSwitchOpenStatus)
    }

    private fun setDeviceInfo() {
        var name = mEditTextName.text.toString()
        val number = mEditTextNumber.text.toString().toLong()
        val isOpen = mSwitchOpenStaus.isChecked
        mDeviceInfo = DeviceInfo(name, number, isOpen)
    }

    private fun setDeviceInfoViews() {
        mEditTextName.setText(mDeviceInfo.name)
        mEditTextNumber.setText(mDeviceInfo.number.toString())
        mSwitchOpenStaus.isChecked = mDeviceInfo.isOpen
    }

    private fun secondActivityRequestProc(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {

            (data?.getSerializableExtra(DEVICE_INFO) as DeviceInfo).also {
                mDeviceInfo = it
                setDeviceInfoViews()
            }

        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }

    }

    private fun thirdActivityRequestProc(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            setTitle("Third activity OK")
        } else {
            Toast.makeText(this, "Third activity Cancelled", Toast.LENGTH_SHORT).show()
            setTitle("")
        }

    }

    private fun initialize() {
        val appName = resources.getString(R.string.app_name)
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SECOND_ACTIVITY_REQUEST_CODE -> {
                secondActivityRequestProc(resultCode, data)
            }
            THIRD_ACTIVITY_REQUEST_CODE -> {
                thirdActivityRequestProc(resultCode, data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onOpenSecondActivityClicked(view: View) {

        Intent(this, SecondActivity::class.java).also {
            setDeviceInfo()
            it.putExtra(DEVICE_INFO, mDeviceInfo)
            startActivityForResult(it, SECOND_ACTIVITY_REQUEST_CODE)
        }
    }

    fun onOpenThirdActivityClicked(view: View) {
        Intent(this, ThirdActivity::class.java).also {
            startActivityForResult(it, THIRD_ACTIVITY_REQUEST_CODE)
        }
    }
}